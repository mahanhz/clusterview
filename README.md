# clusterview

[![Build Status](https://travis-ci.org/mahanhz/clusterview.svg?branch=master)](https://travis-ci.org/mahanhz/clusterview)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/e5ac24c87a284c539578f2655539bf2c)](https://www.codacy.com/app/mahanhz/clusterview?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=mahanhz/clusterview&amp;utm_campaign=Badge_Grade)
[![Coverage Status](https://coveralls.io/repos/github/mahanhz/clusterview/badge.svg?branch=master)](https://coveralls.io/github/mahanhz/clusterview?branch=master)
[![Average time to resolve an issue](http://isitmaintained.com/badge/resolution/mahanhz/clusterview.svg)](http://isitmaintained.com/project/mahanhz/clusterview "Average time to resolve an issue")
[![Percentage of issues still open](http://isitmaintained.com/badge/open/mahanhz/clusterview.svg)](http://isitmaintained.com/project/mahanhz/clusterview "Percentage of issues still open")

A view into the details of each cluster.

## Cloud deployment

* Create an account on Pivotal Web Services
* Install the CLI

### To login

cf login -a api.run.pivotal.io

### To deploy the app

cf push clusterview -p /build/libs/clusterview-1.0.0.jar

### To view the logs

cf logs clusterview

### To restage the application

cf restage clusterview