import { Client } from '@stomp/stompjs';

let stompClient: Client | null = null;

export function connectWebSocket(
  url: string,
  topic: string,
  callback: (progress: number) => void
) {
  stompClient = new Client({
    brokerURL: url,
    reconnectDelay: 5000,
    heartbeatIncoming: 4000,
    heartbeatOutgoing: 4000,
  });

  stompClient.onConnect = () => {
    stompClient?.subscribe(topic, (message) => {
      const progress = JSON.parse(message.body).progress;
      callback(progress);
    });
  };

  stompClient.activate();
}

export function disconnectWebSocket() {
  if (stompClient) {
    stompClient.deactivate();
    stompClient = null;
  }
}