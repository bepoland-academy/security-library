 pipeline {
   agent any
   stages {
     stage('Checkout Sources') {
       steps {
         git(url: 'https://github.com/bepoland-academy/security-library.git', branch: 'development')
       }
     }
     stage('Compile') {
       steps {
         sh 'mvn clean install'
       }
     }
   }
 }