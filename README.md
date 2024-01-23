# Projeto-final-modulo1-Java

Projeto
Crie uma agenda de contatos, onde pode ser registrados vários contatos e seus dados. A aplicação terá o seguinte formato:
 
##################
##### AGENDA #####
##################
>>>> Contatos <<<<
Id | Nome
1  | Alex Araujo
2  | Joao Gomes
3  | Silvio Santos
>>>> Menu <<<<
1 - Adicionar Contato
2 - Remover Contato
3 - Editar Contato
4 - Sair


 
public class Contato {
    private Long id;
    private String nome;
    private String sobreNome;
    private List<Telefone> telefones;
}
public class Telefone {
    private Long id;
    private String ddd;
    private Long numero;
}
Requisitos Não-Funcionais
Utilizar arquivos de texto para armazenar os dados (Simular base de dados)
/

Requisitos Funcionais
RN1: Não é permitido armazenar contatos com o mesmo id;
RN2: Não é permitido armazenar contatos com telefones ja cadastrados; 
RN3: Para realizar as ações, será necessário informar o id do contato;
