# TCP_chat_room

- A TCP server: it receives and stores all messages sent from producer clients. When a new listener client comes, it first sends all stored messages to the client and keeps the client updated when a new message is received.
- A TCP Producer client: it first identifies itself as a producer client to the server and then sends messages.
- A TCP Listener client: it first identifies itself as a listener client to the server, accepts all stored messages from the server, and then continuously receives messages from the server.

Producer  ------
After connected with Server, the Producer first sends the string “PRODUCER” to server, in order to differentiate itself from “LISTENER” clients. Then, it has to open the specified the input file and sends every line to the server until it encounters a line ".bye". It should not send any more messages and exits once this line is found. “Name of the producer” is used to differentiate messages from different producers. Each message has to be prepended with the name of the producer followed by a colon.
For example, if a producer named "1" first connets to the server and reads a line of message "Hello" from the inputfile, it should send two lines to the server: first "PRODUCER" (no need to attach the name here) and "1:Hello" ("1:" is the name of the producer). 

Listener -----------
The implementation of Listener is inside Listener.java. It takes two command line parameters: the name and the port number of the server. It first also sends the string “LISTENER” to server. It then continuously receives the messages sent from the server infinitely. It has to print the messages to stdout as they are received.
