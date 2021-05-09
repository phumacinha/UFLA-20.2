import requests

def print_count(server_response_count: dict):
    for server, count in server_response_count.items():
        print(f'{server} responded {count} time(s)')

def print_response(request_index: int, server_identifier: str):
    server_color = {'server-1': '\033[31m', 'server-2': '\033[32m', 'server-3': '\033[93m', 'end': '\033[0m'}
    color = server_color[server_identifier]
    end_color = server_color['end'];
    print(f'{request_index:>2} - request got response from {color}{server_identifier}{end_color}')

def send_requests(number_of_requests: int):
    server_response_count = {'server-1': 0, 'server-2': 0, 'server-3': 0}

    for i in range(1, number_of_requests + 1):
        try:
            response = requests.get('http://localhost:8080/')
            server_identifier = response.text;
            server_response_count[server_identifier] += 1

            print_response(i, server_identifier)
        except:
            print(f'{i:>2} - can\'t reach the server')

    return server_response_count

def main():
    server_response_count = send_requests(15)
    print()
    print_count(server_response_count)

if __name__ == "__main__":
    main()