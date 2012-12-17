require 'corba'
CORBA.implement('Test.idl')

orb = CORBA.ORB_init('myORB')

obj = orb.string_to_object('file://server.ior')
echo_obj = Test::Echo._narrow(obj)

input = 'Hello world!'
puts "sending \'#{input}\' to server"
output = echo_obj.echo_string(input)
puts "server returned \'#{output}\'"

echo_obj.shutdown()
orb.destroy()
