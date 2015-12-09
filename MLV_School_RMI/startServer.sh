#!/bin/bash

cd bin
echo Compile UserImpl
rmic fr.mlv.school.UserImpl
echo Compile LibraryImpl
rmic fr.mlv.school.LibraryImpl
echo Compile BookImpl
rmic fr.mlv.school.BookImpl
echo Compile CommentImpl
rmic fr.mlv.school.CommentImpl
echo Starting registry
rmiregistry