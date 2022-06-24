import binascii
from scapy.all import *
from scapy.layers.l2 import Ether


def formatting_mac_address(x):
    result = str(str(x)[2:4] + ":" +
                 str(x)[4:6] + ":" + str(x)[6:8] + ":" +
                 str(x)[8:10] + ":" + str(x)[10:12] + ":" +
                 str(x)[12:14]).replace(" ", "")
    return result


def formatting_hex(y):
    length = len(str(y))
    result = str("0x" + str(y)[2:length - 1]).replace(" ", "")
    return result


packets = PcapReader('assignment4_my_arp.pcap')
for packet in packets:

    pkt_hex = binascii.hexlify(
        bytes(packet))  # convert from Ether to bytes, then use binascii to convert packet info to hex
    ethernet_header = struct.unpack("!6s6s2s", (bytes(packet))[0:14])  # convert header info to bytes type, use
    # struct to unpack ethernet header, create tuple
    ethertype = str(ethernet_header[2])[2:10]  # convert to string, slice to get 4 digits
    if ethertype == "\\x08\\x06":  # 0806 is the ethertype for ARP packet, if it is equal to 0x0806, then proceed
        arp_header = struct.unpack("2s2s1s1s2s6s4s6s4s", (bytes(packet))[14:42])  # convert packet to bytes, slicing byte
        # 14-41, use struct to unpack arp header, create tuple
        mac_src = packet[Ether].src  # derive source mac address using scapy and store to a new variable
        ip_src = socket.inet_ntoa(arp_header[6])  # item 6 in unpacked arp header is the ip address of source
        mac_dst = packet[Ether].dst  # derive destination mac address using scapy and store to a new variable
        ip_dst = socket.inet_ntoa(arp_header[8])  # item 8 in unpacked arp header is the ip address of destination
        hardware_type = pkt_hex[28:32]  # bytes 28-31 indicate hardware type
        protocol_type = pkt_hex[32:36]  # bytes 32-35 indicate protocol type
        hardware_size = pkt_hex[36:38]  # bytes 36-37 indicate hardware size
        protocol_size = pkt_hex[38:40]  # bytes 38-39 indicate protocol type
        opcode = pkt_hex[40:44]  # bytes 40-43 indicate the opcode

        if str(opcode)[5] == "1":  # (str(opcode)[5]) is the last digit of the opcode, 1 or 2
            infoType = "REQUEST"  # 1 means is the request
        else:
            infoType = "RESPONSE"  # 2 means is the response

        print("----------------------------------------------------")
        print(infoType, "INFO: ")  # print whether it is a request or response
        print("        Hardware Type:           ", formatting_hex(hardware_type))
        print("        Protocol Type:           ", formatting_hex(protocol_type))
        print("        Hardware Size:           ", formatting_hex(hardware_size))
        print("        Protocol Size:           ", formatting_hex(protocol_size))
        print("        Opcode:                  ", formatting_hex(opcode))
        print("        Source MAC Address:      ", mac_src)
        print("        Source IP Address:       ", ip_src)
        print("        Dest MAC Address:        ", mac_dst)
        print("        Dest IP Address:         ", ip_dst)
    else:  # if type is not 0806, it is not an ARP packet
        print("Not an ARP packet.")
