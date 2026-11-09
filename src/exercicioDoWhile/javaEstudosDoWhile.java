import java.util.Scanner;

void main() {
    Scanner input = new Scanner(System.in);

    final String SENHA_CORRETA = "102030";
    final int LIMITE_ERROS_SAQUE = 5;

    boolean autenticado = false;
    int tentativasLogin = 3;
    int errosSenhaSaque = 0;

    System.out.println("==$== BEM VINDO AO BANCO HARMONY ==$==");

    // 1. Controle de Acesso Inicial (Autenticação)
    while (tentativasLogin > 0 && !autenticado) {
        System.out.print("Digite sua senha de 6 dígitos para acessar: ");
        String senhaDigitada = input.next();

        if (senhaDigitada.equals(SENHA_CORRETA)) {
            autenticado = true;
            System.out.println("\n✓ Acesso autorizado com sucesso!");
        } else {
            tentativasLogin--;
            if (tentativasLogin > 0) {
                System.out.printf("|- Senha incorreta! Tentativas restantes: %d -|\n\n", tentativasLogin);
            } else {
                System.out.println("\n|- CONTA BLOQUEADA NO LOGIN: Limite de tentativas excedido! -|");
            }
        }
    }

    if (!autenticado) {
        input.close();
        return;
    }

    // 2. Menu Principal de Operações
    double saldo = 0;
    int option;
    boolean contaBloqueada = false;

    do {
        System.out.println("\nEscolha uma opção:\n[1] SALDO\n[2] DEPOSITAR\n[3] SACAR\n[4] SAIR");
        System.out.print("Opção: ");
        option = input.nextInt();

        switch (option) {
            case 1 -> System.out.printf("|- Seu saldo é de R$%.2f -|\n", saldo);

            case 2 -> {
                System.out.print("Digite o valor de depósito: R$ ");
                String textoDeposito = input.next().replace(",", ".");
                double deposito = Double.parseDouble(textoDeposito);

                if (deposito > 0) {
                    saldo += deposito;
                    System.out.printf("Depósito de R$%.2f realizado com sucesso!\n", deposito);
                } else {
                    System.out.println("|- Valor inválido para depósito. Digite um valor maior que zero. -|");
                }
            }

            case 3 -> {
                System.out.print("Digite o valor de saque: R$ ");
                String textoSaque = input.next().replace(",", ".");
                double saque = Double.parseDouble(textoSaque);

                if (saque <= 0) {
                    System.out.println("|- Valor inválido para saque. -|");
                } else if (saque > saldo) {
                    System.out.println("|- Saldo insuficiente -|");
                } else {
                    // Confirmação de senha para saque
                    System.out.print("Confirme sua senha de 6 dígitos para autorizar o saque: ");
                    String senhaConfirmacao = input.next();

                    if (senhaConfirmacao.equals(SENHA_CORRETA)) {
                        saldo -= saque;
                        errosSenhaSaque = 0; // zera os erros acumulados após acertar
                        System.out.printf("✓ Saque de R$%.2f autorizado e realizado com sucesso!\n", saque);
                    } else {
                        errosSenhaSaque++;
                        int restantes = LIMITE_ERROS_SAQUE - errosSenhaSaque;

                        if (errosSenhaSaque >= LIMITE_ERROS_SAQUE) {
                            System.out.println("\n" + "=".repeat(60));
                            System.out.println("|- [ALERTA DE SEGURANÇA] CONTA BLOQUEADA! -|");
                            System.out.println("Você errou a senha de saque 5 vezes consecutivas.");
                            System.out.println("Por favor, procure a agência mais próxima com documento oficial.");
                            System.out.println("=".repeat(60));

                            contaBloqueada = true;
                        } else {
                            System.out.printf("|- Senha incorreta! Tentativas restantes de saque: %d -|\n", restantes);
                        }
                    }
                }
            }

            case 4 -> System.out.println("\nOBRIGADO - VOLTE SEMPRE AO BANCO HARMONY!");

            default -> System.out.println("|- Opção inválida! Escolha uma opção/número de 1 a 4. -|");
        }

        // Se a conta for bloqueada no saque, interrompe o laço imediatamente
        if (contaBloqueada) {
            break;
        }

    } while (option != 4);

    input.close();
}