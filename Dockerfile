FROM amazoncorretto:17

COPY .mvn/ .mvn
COPY mvnw pom.xml ./

RUN ./mvnw dependency:go-offline

WORKDIR /project

COPY src ./src

CMD ["./mvnw"]

# build command from main parent root
# docker build -t mbbackend .
# run command from from main parent root
# docker run --rm -v ${PWD}:/project mbbackend ./mvnw test