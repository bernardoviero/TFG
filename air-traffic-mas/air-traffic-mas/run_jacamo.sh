#!/bin/bash

echo "Iniciando JaCaMo MAS..."

./gradlew run --args="air_traffic_mas.jcm" &

sleep 5

echo "Iniciando configuração automática dos agentes..."
curl -X POST http://localhost:3273/workspaces/env/artifacts/agent_data/operations/initialize_agents

echo "Sistema e agentes configurados com sucesso!"
