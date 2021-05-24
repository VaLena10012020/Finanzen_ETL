#!/bin/bash

set -e # exit immediately in case of error
set -x # print all executed commands (=debug mode)

name="finanzen_etl"

echo "=== Get latest docker image ==="

# get latest docker image for caching if available
docker pull ${ECR_REGISTRY}/${ECR_REPOSITORY}:finanzen_etl || true


echo "=== Build new docker image ==="

docker build --pull=true --cache-from ${ECR_REGISTRY}/${ECR_REPOSITORY}:finanzen_etl \
  -t name --build-arg ECR_REPOSITORY=${ECR_REPOSITORY} .

# to do add test script for docker image

echo "=== Push docker image ${name} to AWS ECR ==="

docker push ${ECR_REGISTRY}/${ECR_REPOSITORY}:finanzen_etl
