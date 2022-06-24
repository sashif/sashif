README for analysis_pcap_arp.py

i)
----------------------------------------------------
REQUEST INFO: 
        Hardware Type:            0x0001
        Protocol Type:            0x0800
        Hardware Size:            0x06
        Protocol Size:            0x04
        Opcode:                   0x0001
        Source MAC Address:       0c:54:15:f4:ba:64
        Source IP Address:        172.25.237.191
        Dest MAC Address:         c8:34:8e:76:63:5c
        Dest IP Address:          172.25.236.207
----------------------------------------------------
RESPONSE INFO: 
        Hardware Type:            0x0001
        Protocol Type:            0x0800
        Hardware Size:            0x06
        Protocol Size:            0x04
        Opcode:                   0x0002
        Source MAC Address:       c8:34:8e:76:63:5c
        Source IP Address:        172.25.236.207
        Dest MAC Address:         0c:54:15:f4:ba:64
        Dest IP Address:          172.25.237.191

ii) The MAC Address of my router is 0c:54:15:f4:ba:64, and the IP Address is 
172.25.237.191. I determined this after capturing the packet exchange in Wireshark.
The request asks for information to be sent back to 172.25.237.191, the 
source address, in other words, it is where the request originates (my router).

------------------------------------------------------------------------------------------
INSTRUCTIONS:

1) Save the pcap file in the same file directory as the .py file.

2) Open in IDE and run the program, edit name of pcap file on line 20 if necessary.

------------------------------------------------------------------------------------------
Explanation of program:

First, we will be importing packages; I mainly used the libraries scapy and binascii for 
the purpose of this program.
The function formatting_mac_address is to split the 12 character long address into twos,
and seperate them with colons for formatting purposes (if what we have is a string,
it will literally print it out, so we can use this if it is). Similarly, the formatting_hex
function adds the "0x" to the hex value, to indicate that it must be read as a hex value.
These functions will be used to print values later.
Next, I open the pcap file using PcapReader from scapy. I'll store the packets which are
read into a variable called packets. Next, I will use a for loop to go through the packets
I just stored (for the single message exchange, there are two).
packet itself is of type Ether, so we need to convert bytes in order to translate the value
to hex. That is done using hexlify from binascii. We also convert the header information,
which we obtain from slicing the first 14 characters, to bytes, and use struct to unpack 
it. As a result, we create a tuple, which is stored in ethernet_header.
Next, I converted the third index of this tuple which was just created, into a string.
It must be sliced from index 2 to 9, in order to obtain the ethertype.
Next comes confirming whether this is an ARP packet or not. 0806 is the ethertype for 
ARP packet, if it is equal to 0x0806, then we proceed with the program. If the type does
not match 0806, it is not an ARP packet and the user is informed.
As we did for the ethernet_header, we must also unpack the arp_header using struct after
converted to bytes, before slicing from 14-41. A tuple is created. By doing this, we can
obtain information such as the IP addresses. Index 6 has the ip address of the source,
which we store in ip_src. Similarly, index 8 has the destination ip address, and we store
that too.Next, we derive the source mac address using scapy and store to a new variable, 
called mac_src. We also can derive the destination mac address and store that to mac_dst.
The next couple of steps will be to slice the hex value of the packet. We can obtain more
information, such as the hardware type, size, protocol type, size, etc. Bytes 28-31 
indicate hardware type, bytes 32-35 indicate protocol type, bytes 36-37 indicate hardware 
size, bytes 38-39 indicate protocol type, bytes 40-43 indicate the opcode. We store all of 
this information into respective variables.
I want to know whether the packet is a request or a response, and we determine that from
the opcode. If the opcode is 1, it is a request, and if it is 2, it is a response. By
converting the opcode value into a string and comparing index 5 (the last digit
of the opcode) to 1, we can easily determine what it is and print that out.
Finally, we print the information type (request or response) for each packet, and the values
that we obtained from the packet. I simply printed out the variables where I stored the
information, and applied the functions I wrote (formatting_hex).
