echo "Generating from IDL..."
idlj -fclient -td src ../echo.idl
if [ $? -ne 0 ]; then { echo "Failed, aborting." ; exit 1; } fi

echo "Compiling classes..."
mkdir -p classes && \
  javac -d classes src/EchoDemo/*java && \
  javac -d classes -classpath classes src/*java
if [ $? -ne 0 ]; then { echo "Failed, aborting." ; exit 1; } fi

echo "Done."
