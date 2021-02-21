'''
    GCC129 - Sistemas Distribuídos
    Pedro Antonio de Souza (201810557)
'''

import socket
import os
import time

NUMBER_OF_PACKETS = 10

udp_ip = '18.224.220.128'
udp_port = 6000

sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
sock.settimeout(0.25)

lost_packets_counter = 0
rtt_list = []

for packet_id in range(0, NUMBER_OF_PACKETS):
    message = 'ping ' + str(packet_id)

    try:
        start_time = time.time()
        
        sock.sendto(message.encode('utf_8'), (udp_ip, udp_port))
        response, address = sock.recvfrom(1024)
        
        end_time = time.time()

        rtt_in_milliseconds = (end_time - start_time)*1000
        rtt_list.append(rtt_in_milliseconds)

        print('\'{}\' received from {} rtt={:.2f} ms'.format(response.decode(), address, rtt_in_milliseconds))

    except socket.timeout:
        lost_packets_counter += 1

    time.sleep(1)

print()

rtt_in_milliseconds_average = sum(rtt_list)/len(rtt_list)
print('{:.2f} ms average RTT'.format(rtt_in_milliseconds_average))

packet_loss_rate = ((lost_packets_counter)/NUMBER_OF_PACKETS) * 100
print('{}% packet loss'.format(packet_loss_rate))