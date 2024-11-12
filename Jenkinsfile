pipeline {
  agent any
  stages {
    stage("Verify Tooling") {
      steps {
        sh '''
          docker version
          docker info
          docker-compose version
          curl --version
          '''
      }
    }
    stage('Prune Docker Data') {
      steps {
        sh 'docker system prune -a --volumes -f'
      }
    }
    stage('Start Containers') {
      steps {
        sh 'docker-compose -f docker-compose.prod.yml up -d --force-recreate --no-color'
        sh 'docker-compose -f docker-compose.prod.yml ps'
      }
    }
    stage('Wait for API to be Ready') {
      steps {
        script {
          def maxRetries = 20
          def retryCount = 0
          while (retryCount < maxRetries) {
            def result = sh(script: 'docker-compose -f docker-compose.prod.yml ps | grep "Up (healthy)" | grep "api"', returnStatus: true)
            if (result == 0) {
              echo "API is ready!"
              break
            } else {
              echo "Waiting for API to be healthy... (Attempt ${retryCount + 1} of ${maxRetries})"
              sleep 5
              retryCount++
            }
          }
          if (retryCount == maxRetries) {
            error("API did not become healthy in time.")
          }
        }
      }
    }
    stage('Run Tests Against the Container') {
      steps {
        sh 'curl http://api:8585'
      }
    }
  }
}
