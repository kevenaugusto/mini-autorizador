# Mini Autorizador
Autorizador para transações de Vale Refeição e Vale Alimentação proposto pela VR Benefícios como desafio técnico para integrar seu time de desenvolvimento. Para visualizar a descrição completa do desafio, [clique aqui](https://github.com/kevenaugusto/mini-autorizador/blob/master/Desafio.md).

# Requisitos
- **[Java JDK 17](https://www.oracle.com/br/java/technologies/downloads/#java17)**
- **[Docker](https://www.docker.com/products/docker-desktop/)**

# Execução do Sistema
- Clone o repositório ou faça download do arquivo ***.zip***
```bash
git clone https://github.com/kevenaugusto/mini-autorizador.git
```
- Abra um terminal dentro da pasta ***docker*** que está na raiz do projeto e execute o seguinte comando:
```bash
docker-compose up -d
```
- Abra o arquivo ***pom.xml*** utilizando a sua IDEA de preferência
- Aguarde o download de todas as dependências do projeto
- Execute o método ***main*** presente na classe ***MiniAuthorizerApplication.java***

# Rotas

## Servers
Ambiente de desenvolvimento local: http://localhost:8080

## Controlador de Cartão
| Método  | Path  | Descrição                          |
| ------------ | ------------ |------------------------------------|
| POST  |  /cartoes | Efetua a criação de um novo cartão |
| GET  |  /cartoes/{numeroCartao} | Recupera o saldo de um cartão      |

## Controlador de Transações
| Método  | Path  | Descrição                         |
| ------------ | ------------ |-----------------------------------|
| POST  |  /transacoes | Efetua uma transação com o cartão |