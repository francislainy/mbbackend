FROM amazoncorretto:17

# Install Docker
RUN amazon-linux-extras install docker \
 && docker --version

COPY entrypoint.sh /usr/local/bin/entrypoint.sh
RUN chmod +x /usr/local/bin/entrypoint.sh

COPY .mvn/ .mvn
COPY mvnw pom.xml ./

RUN ./mvnw dependency:go-offline

WORKDIR /project

COPY src ./src

CMD ["./mvnw"]

# build command from main parent root
# docker build -t mbbackend .
# run command from from main parent root
# docker run --privileged --rm -v ${PWD}:/project mbbackend --entrypoint=entrypoint.sh ./mvnw test
