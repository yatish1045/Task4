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
                sh 'mvn clean compile'
            }
        }
        stage('Unit Tests') {
            steps {
                sh 'mvn test'
            }
        }
       
        stage('OWASP SCAN') {
            steps {
                sh 'dependency-check --scan ./'
                junit 'dependency-check-report.xml'
            }
        }
        stage('Build Artifact') {
            steps {
                sh 'mvn clean install'
            }
        }
    }
}
