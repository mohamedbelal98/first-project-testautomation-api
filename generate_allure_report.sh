#!/bin/bash
parent_path=$( cd "$(dirname "${BASH_SOURCE[0]}")" ; pwd -P )
cd "/Users/radwa/.m2/repository/allure/allure-2.20.1/bin/"
bash allure serve "$parent_path/allure-results"
exit