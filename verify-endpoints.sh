#!/bin/bash
# Enhanced endpoint verification for all song microservices with test feedback via Ingress

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
CURL_RESOLVE="--resolve $INGRESS_HOST:80:127.0.0.1"

# POST
POST_URL="$BASE_URL$POST_PATH"
POST_RESPONSE=$(curl -s $CURL_RESOLVE -X POST -H "Content-Type: application/json" -d "$SONG_JSON" "$POST_URL")
echo "$POST_RESPONSE" | jq .
SONG_ID=$(echo "$POST_RESPONSE" | jq -r '.id')
if [ -z "$SONG_ID" ] || [ "$SONG_ID" == "null" ]; then
  print_result 1 "POST $POST_PATH failed"
  echo "Skipping subsequent tests due to missing SONG_ID."
  exit 1
else
  print_result 0 "POST $POST_PATH passed"
fi

# GET ALL
GET_ALL_URL="$BASE_URL$GET_PATH"
GET_ALL_RESPONSE=$(curl -s $CURL_RESOLVE -X GET "$GET_ALL_URL")
echo "$GET_ALL_RESPONSE" | jq .
FOUND=$(echo "$GET_ALL_RESPONSE" | jq -r ".[] | select(.id == \"$SONG_ID\") | .name")
if [ "$FOUND" == "Test Song" ]; then
  print_result 0 "GET $GET_PATH passed"
else
  print_result 1 "GET $GET_PATH failed"
fi

# GET BY ID
GET_BY_ID_URL="$BASE_URL$GET_PATH/$SONG_ID"
GET_BY_ID_RESPONSE=$(curl -s $CURL_RESOLVE -X GET "$GET_BY_ID_URL")
echo "$GET_BY_ID_RESPONSE" | jq .
RETRIEVED_NAME=$(echo "$GET_BY_ID_RESPONSE" | jq -r 'if type=="array" then .[0].name else .name end')
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
UPDATED_RETRIEVED=$(curl -s $CURL_RESOLVE -X GET "$GET_BY_ID_URL" | jq -r 'if type=="array" then .[0].name else .name end')
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
