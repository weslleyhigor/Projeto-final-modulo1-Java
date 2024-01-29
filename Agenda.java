import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Telefone {
    private Long numero;

    public Telefone(Long numero) {
        this.numero = numero;
    }

    public Long getNumero() {
        return numero;
    }
}

class Contato {
    private Long id;
    private String nome;
    private List<Telefone> telefones;

    public Contato(Long id, String nome) {
        this.id = id;
        this.nome = nome;
        this.telefones = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public List<Telefone> getTelefones() {
        return telefones;
    }

    public void setNome(String novoNome) {
        this.nome = novoNome;
    }

    public void adicionarTelefone(Long numero) {
        Telefone telefone = new Telefone(numero);
        telefones.add(telefone);
    }
}

public class Agenda {
    private List<Contato> contatos;
    private static final String ARQUIVO_CONTATOS = "contatos.txt";

    public Agenda() {
        this.contatos = new ArrayList<>();
        carregarContatos();
    }

    public void mostrarMenu() {
        System.out.println("\n##################");
        System.out.println("##### AGENDA #####");
        System.out.println("##################");
        System.out.println(">>>> Contatos <<<<");
        System.out.println("Id | Nome | Telefone");

        for (Contato contato : contatos) {
            System.out.print(contato.getId() + "  | " + contato.getNome() + " | ");
            List<Telefone> telefones = contato.getTelefones();
            for (Telefone telefone : telefones) {
                System.out.print(telefone.getNumero() + " ");
            }
            System.out.println();
        }


        System.out.println("\n>>>> Menu <<<<");
        System.out.println("1 - Adicionar Contato");
        System.out.println("2 - Remover Contato");
        System.out.println("3 - Editar Contato");
        System.out.println("4 - Sair");

        int escolha = obterEscolhaUsuario();
        processarEscolha(escolha);
    }

    private int obterEscolhaUsuario() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nEscolha uma opção: ");
        return scanner.nextInt();
    }

    private void processarEscolha(int escolha) {
        switch (escolha) {
            case 1:
                adicionarContato();
                break;
            case 2:
                removerContato();
                break;
            case 3:
                editarContato();
                break;
            case 4:
                salvarContatos();
                System.out.println("Agenda encerrada.");
                System.exit(0);
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
        }
    }

    private void adicionarContato() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Informe o nome: ");
        String nome = scanner.nextLine();

        System.out.print("Informe o id: ");
        Long id = scanner.nextLong();
        if (contatoComIdExiste(id)) {
            System.out.println("Já existe um contato com o mesmo id. Operação cancelada.");
            return;
        }

        Contato novoContato = new Contato(id, nome);

        System.out.print("Informe o número de telefone: ");
        Long numeroTelefone = scanner.nextLong();
        if (telefoneJaCadastrado(numeroTelefone)) {
            System.out.println("Já existe um contato com o mesmo número de telefone. Operação cancelada.");
            return;
        }

        novoContato.adicionarTelefone(numeroTelefone);

        contatos.add(novoContato);

        mostrarMenu();
    }

    private boolean contatoComIdExiste(Long id) {
        for (Contato contato : contatos) {
            if (contato.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    private boolean telefoneJaCadastrado(Long numeroTelefone) {
        for (Contato contato : contatos) {
            for (Telefone telefone : contato.getTelefones()) {
                if (telefone.getNumero().equals(numeroTelefone)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void removerContato() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Informe o id do contato a ser removido: ");
        Long id = scanner.nextLong();

        Contato contatoRemover = null;
        for (Contato contato : contatos) {
            if (contato.getId().equals(id)) {
                contatoRemover = contato;
                break;
            }
        }

        if (contatoRemover != null) {
            contatos.remove(contatoRemover);
            System.out.println("Contato removido com sucesso.");
        } else {
            System.out.println("Contato não encontrado.");
        }
    }

    private void editarContato() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Informe o id do contato a ser editado: ");
        Long id = scanner.nextLong();

        Contato contatoEditar = null;
        for (Contato contato : contatos) {
            if (contato.getId().equals(id)) {
                contatoEditar = contato;
                break;
            }
        }

        if (contatoEditar != null) {
            System.out.print("Informe o novo nome completo: ");
            scanner.nextLine();
            String novoNome = scanner.nextLine();
            contatoEditar.setNome(novoNome);

            System.out.print("Deseja editar o telefone? (S/N): ");
            String opcaoTelefone = scanner.next();
            if (opcaoTelefone.equalsIgnoreCase("S")) {
                System.out.print("Informe o novo número de telefone: ");
                Long novoNumeroTelefone = scanner.nextLong();
                contatoEditar.getTelefones().clear();
                contatoEditar.adicionarTelefone(novoNumeroTelefone);
            }

            System.out.println("Contato editado com sucesso.");
        } else {
            System.out.println("Contato não encontrado.");
        }
    }


    private void carregarContatos() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_CONTATOS))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(",");
                Long id = Long.parseLong(dados[0]);
                String nome = dados[1];
                Contato contato = new Contato(id, nome);

                for (int i = 2; i < dados.length; i++) {
                    Long numeroTelefone = Long.parseLong(dados[i]);
                    contato.adicionarTelefone(numeroTelefone);
                }

                contatos.add(contato);
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar contatos do arquivo.");
        }
    }

    private void salvarContatos() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_CONTATOS))) {
            for (Contato contato : contatos) {
                writer.write(contato.getId() + "," + contato.getNome());

                List<Telefone> telefones = contato.getTelefones();
                for (Telefone telefone : telefones) {
                    writer.write("," + telefone.getNumero());
                }

                writer.write("\n");
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar contatos no arquivo.");
        }
    }

    public static void main(String[] args) {
        Agenda agenda = new Agenda();
        while (true) {
            agenda.mostrarMenu();
        }
    }
}
