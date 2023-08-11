#!/usr/bin/env bash
export DOCKER_BUILDKIT=1
docker build --platform linux/amd64 -t aigc-sd-qc:0.0.1 -f Dockerfile_java .