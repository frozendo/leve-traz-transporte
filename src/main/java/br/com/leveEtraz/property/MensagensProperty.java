package br.com.leveEtraz.property;


/**
 * Key property para a tela principal do cockpit
 */
public enum MensagensProperty {

    /**
     * Mensagem de mapa cadastrado com sucesso
     */
    MSG_CADASTRO_SUCESSO("Mapa cadastrado com sucesso"),

    /**
     * Campo nome obrigatorio
     */
    MSG_ERRO_01("O campo nome é obrigatório"),

    /**
     * Mapa já cadastrado
     */
    MSG_ERRO_02("Mapa já cadastrado. Por favor verifique!"),

    /**
     * Capacidade máxima de nome excedida
     */
    MSG_ERRO_03("Capacidade excedida. Informe até 30 caracteres para o Nome do mapa. Quantidade atual "),

    /**
     * Origem não informada
     */
    MSG_ERRO_04("Uma ou mais rotas não possuem origem. O campo origem é obrigatório"),

    /**
     * Destino não informado
     */
    MSG_ERRO_05("Uma ou mais rotas não possuem destino. O campo destino é obrigatório"),

    /**
     * Distancia não informada
     */
    MSG_ERRO_06("Uma ou mais rotas não possuem distância. O campo distância é obrigatório"),

    /**
     * Capacidade origem excedida
     */
    MSG_ERRO_07("Uma ou mais rotas com capacidade de campo excedida. Informe até 30 caracteres para a Origem da rota. Quantidade atual "),

    /**
     * Capacidade destino excedida
     */
    MSG_ERRO_08("Uma ou mais rotas com capacidade de campo excedida. Informe até 30 caracteres para a Destino da rota. Quantidade atual "),

    /**
     * Rotas já cadastradas para o mapa
     */
    MSG_ERRO_09("Uma ou mais rotas informadas já estão cadastradas para esse mapa. Por favor verifique!"),

    /**
     * Mapa não informado
     */
    MSG_ERRO_10("Nome do mapa não informado"),

    /**
     * Origem não informada
     */
    MSG_ERRO_11("Ponto de origem da rota não informado"),

    /**
     * Destino não informado
     */
    MSG_ERRO_12("Ponto de destino da rota não informado"),

    /**
     * Autonomia veiculo não informada
     */
    MSG_ERRO_13("Autonomia do veículo não informada"),

    /**
     * Valor combustivel não informado
     */
    MSG_ERRO_14("Valor do combustível não informado"),

    /**
     * Nenhuma rota encontrada
     */
    MSG_ERRO_15("Nenhuma rota encontrada entre os pontos solicitados"),

    /**
     * Mensagem de erro generica 01
     */
    MSG_ERRO_PROCESO_01("Erro no cadastro do mapa: "),

    /**
     * Mensagem de erro generica 02
     */
    MSG_ERRO_PROCESO_02("Erro(s) no cadastro do mapa: "),

    /**
     * Mensagem de erro generica 03
     */
    MSG_ERRO_PROCESO_03("Erro ao processar a solicitação. Entre em contato com o responsável pelo sistema"),

    /**
     * Mensagem de erro generica 04
     */
    MSG_ERRO_PROCESO_04("Erro(s) ao buscar a melhor rota: "),

    /**
     * Mensagem de erro generica 05
     */
    MSG_ERRO_PROCESO_05("Erro ao buscar a melhor rota.");

    /**
     * Key property
     */
    private String mensagem;

    /**
     * Construtor
     * @param key
     */
    private MensagensProperty(String mensagem) {
        this.mensagem = mensagem;
    }

	public String getMensagem() {
		return mensagem;
	}   
    
}
