# Example of Ktor withContext return null in dev mode 


## Build
`./gradlew clean build`

## Run
`./gradlew run` - run in dev mode == true

`./gradlew run -Pconfig.resource=application.prod.conf` - run in dev mode == false

## Steps for reproduce

1. `curl -i -X GET localhost:8080/first` - return `contextValue` for devMode==true and devMode==false
2. `curl -i -X GET localhost:8080/second` - return `Internal Server Error` for devMode==true and `contextValue` for devMode==false
3. `curl -i -X GET localhost:8080/third` - return `contextValue` for devMode==true and devMode==false

