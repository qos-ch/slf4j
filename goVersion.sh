

VER=$1
echo "Will use version '${VER}'"
echo "Changing pom.xml files"
find . -name "pom.xml" |grep -v archetype-resources|xargs perl version.pl ${VER}
echo "Changing Java files"
find . -name "StaticLoggerBinder.java" |grep -v archetype-resources|xargs perl binderVersion.pl ${VER}
