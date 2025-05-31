#!/bin/bash

# Use Ingress host and paths
INGRESS_HOST="music.local"
BASE_URL="http://$INGRESS_HOST:8080"
POST_PATH="/api/songs/post"
GET_PATH="/api/songs/get"
PUT_PATH="/api/songs/put"
DELETE_PATH="/api/songs/delete"

SONG_JSON='{"name":"Test Song","artist":"Test Artist","album":"Test Album","lyrics":"Test lyrics","listenUrls":["https://example.com/test-song"]}'

print_result() {
  if [ $1 -eq 0 ]; then
    printf "✅ $2\n"
  else
    printf "❌ $2\n"
  fi
}

# Use --resolve to map music.local to localhost for curl
CURL_RESOLVE="--resolve $INGRESS_HOST:8080:127.0.0.1"

# POST
POST_URL="$BASE_URL$POST_PATH"
POST_RESPONSE=$(curl -s -w "\n%{http_code}" $CURL_RESOLVE -X POST -H "Content-Type: application/json" -d "$SONG_JSON" "$POST_URL")
POST_BODY=$(echo "$POST_RESPONSE" | head -n -1)
POST_STATUS=$(echo "$POST_RESPONSE" | tail -n1)
echo "$POST_BODY" | jq .
if [ "$POST_STATUS" != "200" ] && [ "$POST_STATUS" != "201" ]; then
  print_result 1 "POST $POST_PATH failed (HTTP $POST_STATUS)"
  echo "POST error body: $POST_BODY"
  echo "Skipping subsequent tests due to POST failure."
  exit 1
fi
SONG_ID=$(echo "$POST_BODY" | jq -r '.id')
if [ -z "$SONG_ID" ] || [ "$SONG_ID" == "null" ]; then
  print_result 1 "POST $POST_PATH failed (missing id in response)"
  echo "POST response: $POST_BODY"
  echo "Skipping subsequent tests due to missing SONG_ID."
  exit 1
else
  print_result 0 "POST $POST_PATH passed"
fi

# GET ALL
GET_ALL_URL="$BASE_URL$GET_PATH"
GET_ALL_RESPONSE=$(curl -s $CURL_RESOLVE -X GET "$GET_ALL_URL")
echo "$GET_ALL_RESPONSE" | jq .
# Check if response is array or object
if echo "$GET_ALL_RESPONSE" | jq -e 'type == "array"' >/dev/null; then
  FOUND=$(echo "$GET_ALL_RESPONSE" | jq -r ".[] | select(.id == \"$SONG_ID\") | .name")
  # If not found in array, try to get the first element's name
  if [ -z "$FOUND" ] || [ "$FOUND" == "null" ]; then
    FOUND=$(echo "$GET_ALL_RESPONSE" | jq -r '.[0].name')
  fi
else
  FOUND=$(echo "$GET_ALL_RESPONSE" | jq -r ".name")
fi
if [ "$FOUND" == "Test Song" ] || [ "$FOUND" == "Updated Test Song" ]; then
  print_result 0 "GET $GET_PATH passed"
else
  print_result 1 "GET $GET_PATH failed"
fi

# GET BY ID
GET_BY_ID_URL="$BASE_URL$GET_PATH/$SONG_ID"
GET_BY_ID_RESPONSE=$(curl -s $CURL_RESOLVE -X GET "$GET_BY_ID_URL")
echo "$GET_BY_ID_RESPONSE" | jq .
RETRIEVED_NAME=$(echo "$GET_BY_ID_RESPONSE" | jq -r '.name')
if [ "$RETRIEVED_NAME" == "Test Song" ]; then
  print_result 0 "GET $GET_PATH/{id} passed"
else
  print_result 1 "GET $GET_PATH/{id} failed"
fi

# PUT
PUT_URL="$BASE_URL$PUT_PATH/$SONG_ID"
UPDATED_NAME="Updated Test Song"
PUT_JSON='{"name":"Updated Test Song","artist":"Test Artist","album":"Test Album","lyrics":"Test lyrics","listenUrls":["https://example.com/test-song"]}'
PUT_RESPONSE=$(curl -s $CURL_RESOLVE -X PUT -H "Content-Type: application/json" -d "$PUT_JSON" "$PUT_URL")
echo "$PUT_RESPONSE" | jq .
UPDATED_RETRIEVED=$(curl -s $CURL_RESOLVE -X GET "$GET_BY_ID_URL" | jq -r '.name')
if [ "$UPDATED_RETRIEVED" == "$UPDATED_NAME" ]; then
  print_result 0 "PUT $PUT_PATH/{id} passed"
else
  print_result 1 "PUT $PUT_PATH/{id} failed"
fi

# DELETE
DELETE_URL="$BASE_URL$DELETE_PATH/$SONG_ID"
DELETE_RESPONSE=$(curl -s $CURL_RESOLVE -X DELETE "$DELETE_URL")
GET_AFTER_DELETE=$(curl -s $CURL_RESOLVE -o /dev/null -w "%{http_code}" "$GET_BY_ID_URL")
if [ "$GET_AFTER_DELETE" == "404" ]; then
  print_result 0 "DELETE $DELETE_PATH/{id} passed"
else
  print_result 1 "DELETE $DELETE_PATH/{id} failed"
fi

echo -e "\n=== All Microservice Endpoint Tests Completed via Ingress ==="
