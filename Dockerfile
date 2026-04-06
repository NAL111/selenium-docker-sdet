FROM bellsoft/liberica-openjdk-alpine:17.0.18

# Install curl jq
RUN apk add curl jq

# Workspace - create a work directory
WORKDIR /home/selenium-docker

# Add the required files

#ADD target/docker-resources /home/selenium-docker
#ADD target/docker-resources .
ADD target/docker-resources ./
ADD runner.sh               runner.sh
#ADD pom.xml canChangeName.

# Environment Variables:
# BROWSER
# HUB_HOST
# TEST_SUITE
# THREAD_COUNT

# Run the Tests
#ENTRYPOINT java -cp 'libs/*' \
#                -Dselenium.grid.enabled=true \
#                -Dselenium.grid.hubHost=${HUB_HOST} \
#                -Dbrowser=${BROWSER} \
#                org.testng.TestNG \
#                -threadcount ${THREAD_COUNT} \
#                test-suites/${TEST_SUITE}

# Fix for windows if needed
RUN dos2unix runner.sh

# Start the runner.sh
ENTRYPOINT sh runner.sh



