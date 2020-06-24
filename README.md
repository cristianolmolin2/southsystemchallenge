# southsystemchallenge

Aplicação SpringBoot, desenvolvida em Java 8.

Fica observando a pasta %HOME%/data/in

Quando um arquivo .dat for adicionado na pasta executa o processamento de todos os arquivos .dat da pasta

O processamento dos arquivos coleta as informações:
- Quantidade de clientes
- Quantidade de vendedores
- Id da venda mais cara
- Pior vendendor, com base no valor total das vendas de cada vendedor.

Gera um arquivo com os resultados, o nome dos arquivos de resultado seguem o padrão {timestamp_da_conclusão_do_processamento}.done.dat

O arquivo é salvo na pasta %HOME%/data/out
