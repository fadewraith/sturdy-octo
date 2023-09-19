<!-- source - https://youtu.be/7ljEz52zdUM?list=PLhzRPVQgdM8XWl2S8Dbl8oB2YtrsY0i4O -->

1. install custom-webpack -D using npm i
2. angular.json -> build -> "builder": "@angular-builders/custom-webpack:browser", -> "options": {
            "customWebpackConfig": {
              "path": "src/custom-webpack.config.ts"
            }, -> serve -> "builder": "@angular-builders/custom-webpack:dev-server",
3. create custom-webpack.config.ts
4. npm i dotenv-webpack