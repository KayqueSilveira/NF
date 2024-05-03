# Projeto Nota Fiscal (NF)

## Descrição

Este projeto é responsável pelo gerenciamento de notas fiscais. Ele integra com o ERP do cliente, valida a nota fiscal com um órgão do governo, busca dados dos clientes em nosso microserviço de clientes e envia boletos para o microserviço interno de cobrança.

## Funcionalidades

1. **Integração com ERP do cliente**: O sistema se integra com o ERP do cliente via API-Rest e troca de arquivos.
2. **Validação de Nota Fiscal**: A nota fiscal é validada em um órgão do governo via API-Rest.
3. **Busca de Dados dos Clientes**: O sistema busca os dados dos clientes em nosso microserviço de clientes.
4. **Envio de Boletos para Cobrança**: Quando o pagamento for via boleto, o sistema envia-os para o microserviço interno de cobrança.
5. **Tratamento de Exceções**: O sistema retorna uma exceção quando o usuário inserir um token incorreto, quando o usuário estiver incorreto ao criar um token, quando o arquivo do boleto estiver vazio ou não for um arquivo PDF, e quando ocorrer uma falha ao extrair o arquivo.

## Segurança

Como há dados sensíveis sendo trafegados, a segurança é uma parte importante deste projeto. Todas as informações são criptografadas e todas as requisições são autenticadas.

## Performance

O sistema é projetado para lidar com mais de 1000 transações por segundo (TPS). As notas fiscais são recebidas e processadas com resiliência e, em caso de erro, o cliente é notificado.

## Resiliência

O sistema é projetado para ser resiliente a falhas. Em caso de falha ao processar uma nota fiscal, o sistema tentará novamente até que a nota fiscal seja processada com sucesso.

## Dependências

- Microserviço de Clientes
- Microserviço de Cobrança
- ERP do Cliente
- Orgão do Governo para validação de Nota Fiscal

![umlDiagram](https://github.com/KayqueSilveira/NF/assets/48252382/1d8754b9-b4eb-497e-ab8d-4c15b0f22f24)

