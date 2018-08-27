
Creation of two applications which communicate via sockets and form a client server system where:

- Server application is responsible for loading data from the external source (excel or txt file) and transferring data in the database.

- After data has been loaded, the server listens on the port specified in application call for any client request. Request are sent via textual protocol through sockets. After serving client request, the server application stops without creating exceptions.

- Client application connects to specified server and initiates transactional request over socket using textual messages. After getting response from server, client application stops without creating exceptions.
