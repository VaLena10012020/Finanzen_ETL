#!/bin/bash

set -e # exit immediately in case of error
set -x # print all executed commands (=debug mode)

arrIN=(${TRAVIS_REPO_SLUG//// })
name=${arrIN[1]}

echo "=== Build new docker image ==="

docker build --pull=true --cache-from ${ECR_REGISTRY}/${ECR_REPOSITORY}:${name} \
  -t ${ECR_REGISTRY}/${ECR_REPOSITORY}:${name} --build-arg ECR_REPOSITORY=${ECR_REPOSITORY} .

# to do add test script for docker image

echo "=== Push docker image ${name} to AWS ECR ==="

docker push ${ECR_REGISTRY}/${ECR_REPOSITORY}:${name}
