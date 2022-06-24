import datetime
import os
import sys
import struct
import time
import select
import socket

ICMP_ECHO_REQUEST = 8
rtt_time = []  # stores all RTT
rtt_min = float('+inf')
rtt_max = float('-inf')
rtt_sum = 0
rtt_cnt = 0
pckRev = 0
packet_length = 36


def checksum(string):
    csum = 0
    countTo = (len(string) / 2) * 2

    count = 0
    while count < countTo:
        thisVal = string[count + 1] * 256 + string[count]
        csum = csum + thisVal
        csum = csum & 0xffffffff
        count = count + 2

    if countTo < len(string):
        csum = csum + ord(string[len(str) - 1])
        csum = csum & 0xffffffff

    csum = (csum >> 16) + (csum & 0xffff)
    csum = csum + (csum >> 16)
    answer = ~csum
    answer = answer & 0xffff
    answer = answer >> 8 | (answer << 8 & 0xff00)
    return answer


def receiveOnePing(mySocket, ID, timeout, destAddr):
    global rtt_min, rtt_max, rtt_sum, rtt_cnt, pckRev, packet_length
    timeLeft = timeout
    total = 0
    while 1:
        startedSelect = time.time()
        whatReady = select.select([mySocket], [], [], timeLeft)
        howLongInSelect = (time.time() - startedSelect)
        if whatReady[0] == []:  # Timeout
            total = 0
            print("Request timed out.")
            return total

        timeReceived = time.time()
        recPacket, addr = mySocket.recvfrom(1024)

        # Fetch the ICMP header from the IP packet
        icmpHeader = recPacket[20:28]
        packetType, code, mychecksum, packetID, sequence = struct.unpack("bbHHh", icmpHeader)
        packet_length = len(recPacket)
        if ID == packetID:  # check if ID equal to packet ID
            bytesInDouble = struct.calcsize('d')  # use struct to slice bytes, get time info, and store in new var
            timeData = struct.unpack('d', recPacket[28:28 + bytesInDouble])[0]
            rtt_time.append(timeReceived - timeData)  # add this value to rtt time list
            pckRev += 1  # increase count for each time
            total = timeReceived - timeData
            return total
        else:
            return str("Invalid ID, no match found.")

        timeLeft = timeLeft - howLongInSelect
        if timeLeft <= 0:
            total = 0
            print("Request timed out.")
            return total
            break


def sendOnePing(mySocket, destAddr, ID):
    # Header is type (8), code (8), checksum (16), id (16), sequence (16)

    myChecksum = 0
    # Make a dummy header with a 0 checksum.
    # struct -- Interpret strings as packed binary data
    header = struct.pack("bbHHh", ICMP_ECHO_REQUEST, 0, myChecksum, ID, 1)
    data = struct.pack("d", time.time())  # 8 bytes
    # Calculate the checksum on the data and the dummy header.
    myChecksum = checksum(header + data)

    # Get the right checksum, and put in the header
    if sys.platform == 'darwin':
        myChecksum = socket.htons(myChecksum) & 0xffff
        # Convert 16-bit integers from host to network byte order.
    else:
        myChecksum = socket.htons(myChecksum)

    header = struct.pack("bbHHh", ICMP_ECHO_REQUEST, 0, myChecksum, ID, 1)
    packet = header + data

    mySocket.sendto(packet, (destAddr, 1))  # AF_INET address must be tuple, not str
    # Both LISTS and TUPLES consist of a number of objects
    # which can be referenced by their position number within the object


def doOnePing(destAddr, timeout):
    icmp = socket.getprotobyname("icmp")
    # SOCK_RAW is a powerful socket type. For more details see: http://sock-raw.org/papers/sock_raw

    mySocket = socket.socket(socket.AF_INET, socket.SOCK_RAW, icmp)  # creating new socket

    myID = os.getpid() & 0xFFFF  # Return the current process i
    sendOnePing(mySocket, destAddr, myID)
    delay = receiveOnePing(mySocket, myID, timeout, destAddr)

    mySocket.close()
    return delay


def ping(host, timeout=1):
    global rtt_min, rtt_max, rtt_sum, rtt_cnt
    cnt = 0
    # timeout=1 means: If one second goes by without a reply from the server,
    # the client assumes that either the client's ping or the server's pong is lost
    dest = socket.gethostbyname(host)
    print("Pinging " + dest + " using Python:")
    # Send ping requests to a server separated by approximately one second
    try:
        while True:
            cnt += 1
            print(packet_length, "bytes from ", dest, "; time = ", round(doOnePing(dest, timeout) * 1000, 2), "ms")
            time.sleep(1)
    except KeyboardInterrupt:
        delay = doOnePing(dest, timeout)
        print("^C--- ", dest, "ping statistics ---")
        rtt_min = round(min(rtt_time) * 1000, 2) if len(rtt_time) > 0 else 0  # calculate min rtt time, convert to ms,
        # then round to two decimal points
        rtt_max = round(max(rtt_time) * 1000, 2) if len(rtt_time) > 0 else 0  # calculate max rtt, round to 2 decimal pt
        rtt_avg = round(float((sum(rtt_time) / len(rtt_time)) * 1000), 2) if len(rtt_time) > 0 else float('nan')
        # calculate average rtt by dividing sum by number of rtt recorded, convert to ms, then round to 2 decimal pt
        # if no rtt recorded and the divisor is zero, rtt_avg equals to zero to avoid error of dividing by zero
        print('round-trip min/avg/max {:.3f}/{:.3f}/{:.3f} ms'.format(rtt_min, rtt_avg, rtt_max))  # print all values
        time.sleep(5)  # program quit 5 seconds after pressing ctrl+c
    return delay


addr = input("Enter domain name or IP address:")
ping(addr)
