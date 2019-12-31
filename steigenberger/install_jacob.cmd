rem install jacob jar to maven repository
rem copy jacob-1.19-x86.dll(for JDK 32bit) or jacob-1.19-x64.dll(for JDK 64bit) to directory bin of JDK
mvn install:install-file -Dfile=./lib/jacob.jar -DgroupId=com.jacob -DartifactId=jacob -Dversion=1.19 -Dpackaging=jar