#Description of the Task

Creating two applications which must communicate via sockets and will form a client server system where:

Server application is responsible for loading data from the external source (excel or txt file) and transferring data in the database.

After data has been loaded, the server will listen on the port specified in application call for any client request. Request must be sent via textual protocol through sockets. After serving client request, the server application must stop without creating exceptions.

Client application will connect to specified server and initiate transactional request over socket using textual messages. After getting response from server, client application must stop without creating exceptions.
