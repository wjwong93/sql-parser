#!/bin/bash

cd antlr
antlr4 -visitor -o ../src PostgreSQLLexer.g4
antlr4 -visitor -o ../src PostgreSQLParser.g4

cd ../src
rm *.tokens
rm *.interp


cd ..
javac -cp /usr/local/lib/antlr-4.13.1-complete.jar -d out/production/sql_parser_2 src/*.java

./run pgq_read.sql