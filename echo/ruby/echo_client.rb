require 'corba'
CORBA.implement('../echo.idl')

orb = CORBA.ORB_init('myORB')

obj = orb.string_to_object('file://server.ior')
echo_obj = EchoDemo::Echo._narrow(obj)

puts "Echo client."
puts "Enter a string to echo."
puts "Enter 'quit' to quit client."
puts "Enter 'shutdown' to terminate server and exit."

loop do
  print "> "
  input = $stdin.gets.strip
  if input=="shutdown"
    echo_obj.shutdown()
    break
  elsif input=="quit"
    break
  else
    output = echo_obj.echo_string(input)
    puts "server returned: '#{output}'"
  end
end

orb.destroy()
puts "terminated."
