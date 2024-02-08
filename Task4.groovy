pipeline {
    agent any
    tools {
        jdk 'JDK17'
        maven 'maven3'
    }

    stages {
        stage('Git Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/yatish1045/Petclinic.git'
            }
        }
        stage('Code Compile') {
            steps {
                bat label: 'Compile', script: 'mvn clean compile'
            }
        }
        stage('Unit Tests') {
            steps {
                bat label: 'Run Tests', script: 'mvn test'
            }
        }
       
        stage('OWASP SCAN') {
            steps {
                dependencyCheck additionalArguments: ' --scan ./ ', odcInstallation: 'DP-Check'
                dependencyCheckPublisher pattern: '**/dependency-check-report.xml'
            }
        }
        stage('Build Artifact') {
            steps {
                bat label: 'Build Artifact', script: 'mvn clean install'
            }
        }
    }
}
