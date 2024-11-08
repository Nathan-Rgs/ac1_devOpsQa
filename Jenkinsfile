pipeline {
  agent any
  stages {
    stage("verify tooling") {
      steps {
        sh '''
          docker version
          docker info
          docker-compose version
          curl --version
          '''
      }
    }
    stage('Prune Docker data') {
      steps {
        sh 'docker system prune -a --volumes -f'
      }
    }
    stage('Start container') {
      steps {
        sh 'docker-compose -f docker-compose.prod.yml up -d --no-color --wait'
        sh 'docker-compose -f docker-compose.prod.yml ps'
      }
    }
    stage('Run tests against the container') {
      steps {
        sh 'curl http://api:8585'
      }
    }
  }

}