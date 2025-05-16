# Number Converter Project

O projeto é uma aplicação web simples construída com Servlets Java. Ela fornece uma interface para converter números entre as bases decimal, binária e hexadecimal.

Funcionalidades:
- Campo para inserir um número.
- Menus suspensos para selecionar a base de origem (decimal, binário, hexadecimal).
- Menus suspensos para selecionar a base de destino.
- Exibição do resultado da conversão ou mensagens de erro.

## Como gerar o arquivo .war da aplicação?

### Opção 1: Usar o Docker com Maven já instalado (sem precisar instalar nada na sua máquina)

Se você tem o Docker instalado, essa é a maneira mais rápida e isolada:

Passo a passo:
1. Abra um terminal na pasta do seu projeto (onde está o `pom.xml`).
2. Execute o seguinte comando:

```console
docker run --rm -v "$PWD":/app -w /app maven:3.9.9-eclipse-temurin-17 mvn clean package
```

🔍 O que esse comando faz:
- Usa uma imagem oficial do Maven com JDK 17.
- Monta a pasta atual (`$PWD`) no container.
- Executa o `mvn clean package` dentro do container.

Ao final, o `.war` estará na pasta `target/` no seu diretório local.

### Opção 2: Instalar Maven e JDK localmente

Se preferir rodar nativamente na sua máquina:

#### 📥 Instalação do JDK (Java Development Kit):
No Ubuntu/Debian:

```console
sudo apt update
sudo apt install openjdk-17-jdk
```

No Windows:

- Baixe e instale o JDK 17 ou superior.

#### 📥 Instalação do Maven:
No Ubuntu/Debian:

```console
sudo apt install maven
```

No Windows:

- Baixe o Maven em: [https://maven.apache.org/download.cgi](https://maven.apache.org/download.cgi)
- Extraia e adicione o `bin` do Maven ao `PATH`.

#### 📦 Compilação do projeto:
Navegue até o diretório do projeto onde está o `pom.xml` e execute:

```console
mvn clean package
```

#### 📂 Resultado:
O arquivo `.war` será gerado na pasta `target/`.

## Requisitos para gerar um .war com Maven
Certifique-se de que o `pom.xml` contenha:

```xml
<packaging>war</packaging>
```

E que tenha configurado corretamente o plugin de compilação, como:

```xml
<build>
  <plugins>
    <plugin>
      <artifactId>maven-war-plugin</artifactId>
      <version>3.3.2</version>
    </plugin>
  </plugins>
</build>
```