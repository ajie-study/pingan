FROM index.alauda.cn/alaudaorg/builder-java:openjdk8-alpine

COPY /eirene /eirene

RUN cd /eirene && \
    mvn package

RUN cd / && rm -rf /eirene
RUN apk add --no-cache bash

#docker build -t index.alauda.cn/alaudaorg/eirene-ci:0.1 .
#docker push index.alauda.cn/alaudaorg/eirene-ci:0.1

WORKDIR /