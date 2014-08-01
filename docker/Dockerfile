# This dockerfile creates a Webracer server based on the latest version on github.
# Server will be running on port 8080

# use Ubuntu as base
FROM ubuntu

# we offer our service on port 8080
EXPOSE 8080

# add Java 8 repository
RUN sudo apt-get update
RUN sudo apt-get install -y software-properties-common
RUN sudo add-apt-repository -y ppa:webupd8team/java

# install Java 8
RUN sudo apt-get update
RUN echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | sudo /usr/bin/debconf-set-selections
RUN sudo apt-get install -y oracle-java8-installer
RUN sudo apt-get install -y oracle-java8-set-default

# install git
RUN sudo apt-get install -y git

# clone Webracer repo and build it
RUN git clone https://github.com/slintes/Webracer.git
RUN cd Webracer && ./gradlew install

# build image: cd to this directory and execute "docker build -t webracer ."
# start image with: docker run -p 8080:8080 -i -t webracer /bin/bash
# start Webracer: cd Webracer && java -jar repoOsgi/WebracerStarter.jar &
