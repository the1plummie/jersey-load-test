# jersey-load-test
This repo contains simple server and client code to reproduce the lingering connections issue with gcloud HTTP load balancer.

# Instance Prep
sudo vim /etc/apt/sources.list.d/java-8-debian.list

add these two lines
    deb http://ppa.launchpad.net/webupd8team/java/ubuntu trusty main
    deb-src http://ppa.launchpad.net/webupd8team/java/ubuntu trusty main

## update repo and install packages
sudo apt-key adv --keyserver keyserver.ubuntu.com --recv-keys EEA14886
sudo apt-get update
sudo apt-get install -y git oracle-java8-installer

# Load test setup
Provision 3 instances:
    1 for server, fronted with HTTP load balancer
    2 for load generator

## Start server
    ./gradlew clean jettyRun

## Start client, with 64 workers using 64 connections pool
    ./gradlew clean loadtest -Pargs="64 64"
