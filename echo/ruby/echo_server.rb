require 'corba/poa'
CORBA.implement('../Test.idl', {}, CORBA::IDL::SERVANT_INTF)

## CORBA.implement(idlfile, options = {}, genbits)
#    in string idlfile 
#        name of file containing IDL definitions
#    in hash options
#        options hash for the IDL compiler
#        - :includepaths => [] 
#          array of include paths for IDL compiler to search
#    in integer genbits
#        bitmask specifying what Ruby mappings to generate
#        bitmask can be combination (or-ed) of one or more of
#        - CORBA::IDL::CLIENT_STUB
#          specifies to generate and load client stub mappings (default)
#        - CORBA::IDL::SERVANT_INTF
#          specifies to generate and load servant mappings
#          (implies generating client stubs)


class EchoServant < POA::Test::Echo
  def initialize(orb)
    @orb = orb
  end
  def echo_string(input)
    "ECHO: #{input}"
  end
  def shutdown()
    @orb.shutdown
  end
end

# initialize ORB instance
orb = CORBA.ORB_init('myORB')

# resolve a RootPOA reference
obj = orb.resolve_initial_references('RootPOA')
root_poa = PortableServer::POA._narrow(obj)

# get and activate the POAManager
poa_man = root_poa.the_POAManager
poa_man.activate

# create and activate the servant
echo_srv = EchoServant.new(orb)
echo_oid = root_poa.activate_object(echo_srv)

# get the stringified IOR for the activated servant and save to a file for the client to use
# IOR = interoperable object reference
#       a. the "contact details" that a client app uses to talk to a CORBA obj
#       b. the RMI-IIOP reference that uniquely identified an object on a remote CORBA server
echo_obj = root_poa.id_to_reference(echo_oid)
echo_ior = orb.object_to_string(echo_obj)
open('server.ior', 'w') { |io|
  io.write echo_ior
}

# run the ORB request handling loop
orb.run

puts "Waiting for client request"

