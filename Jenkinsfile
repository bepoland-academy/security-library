pipeline {
  agent any
  stages {
    stage('Git') {
      steps {
        git(url: 'https://github.com/bepoland-academy/security-library', branch: 'development')
      }
    }
    stage('Build Library') {
      steps {
        sh 'mvn clean install'
      }
    }
  }
}