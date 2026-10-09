package tech.w3ll.egameleague.controllers;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import tech.w3ll.egameleague.HelloApplication;
import tech.w3ll.egameleague.modelos.Partida;
import tech.w3ll.egameleague.modelos.Tabela;
import tech.w3ll.egameleague.modelos.Time;
import tech.w3ll.egameleague.repositores.listas.TimeRepoLista;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TabelaController {

    @FXML private VBox containerDasRodadas;
    @FXML private Button botaoJogarRodada;
    @FXML private Button botaoEditarRodada;
    @FXML private ScrollPane scrollPanePrincipal;

    private Tabela tabela;
    private int rodadaAtualNumero = 0;
    private boolean isEditing = false;
    private List<ObservableList<Partida>> historicoDeRodadas = new ArrayList<>();

    private static final double ALTURA_DA_LINHA = 48.0;

    @FXML
    public void initialize() {
        carregarDadosIniciais();
    }


    private void carregarDadosIniciais() {

        try {

            TimeRepoLista repositorioDeTimes = new TimeRepoLista();

            repositorioDeTimes.resetarTabela();

            repositorioDeTimes.adicionar(new Time("Breno"));
            repositorioDeTimes.adicionar(new Time("Dalmatas"));
            repositorioDeTimes.adicionar(new Time("Kauã"));
            repositorioDeTimes.adicionar(new Time("Davi"));
            repositorioDeTimes.adicionar(new Time("Pedro"));
            repositorioDeTimes.adicionar(new Time("Paulo"));
            repositorioDeTimes.adicionar(new Time("Lucas"));
            repositorioDeTimes.adicionar(new Time("Gabriel"));
            repositorioDeTimes.adicionar(new Time("Matheus"));
            repositorioDeTimes.adicionar(new Time("Felipe"));
            repositorioDeTimes.adicionar(new Time("Gustavo"));
            repositorioDeTimes.adicionar(new Time("Rafael"));
            repositorioDeTimes.adicionar(new Time("Bruno"));
            repositorioDeTimes.adicionar(new Time("Thiago"));
            repositorioDeTimes.adicionar(new Time("Rodrigo"));
            repositorioDeTimes.adicionar(new Time("Vinicius"));

            iniciarTorneio(new ArrayList<>(repositorioDeTimes.listarTodos()));

        } catch (Exception e) {
            System.out.println("Erro ao carregar dados iniciais: " + e.getMessage());
        }
    }

    public void iniciarTorneio(ArrayList<Time> times) {
        if (times.size() < 2) {
            return;
        }

        this.tabela = new Tabela(times);
        this.tabela.embaralharTimes();
        this.rodadaAtualNumero = 0;
        this.historicoDeRodadas.clear();
        this.containerDasRodadas.getChildren().clear();

        avancarParaProximaRodada();
    }

    private void avancarParaProximaRodada() {
        if (rodadaAtualNumero > 0) {
            ObservableList<Partida> ultimaRodada = historicoDeRodadas.get(historicoDeRodadas.size() - 1);
            tabela.avancarParaProximaFase(ultimaRodada);
            desabilitarTabelaAnterior();
        }

        if (tabela.temCampeao()) {
            exibirCampeao();
            return;
        }

        rodadaAtualNumero++;
        tabela.gerarConfrontos();
        List<Partida> novasPartidas = tabela.getPartidasAtuais();
        ObservableList<Partida> partidasObservaveis = FXCollections.observableArrayList(novasPartidas);
        historicoDeRodadas.add(partidasObservaveis);

        adicionarVisualDaRodada(partidasObservaveis);

        botaoJogarRodada.setText("Jogar " + gerarNomeDaRodada(partidasObservaveis.size()));
        botaoJogarRodada.setDisable(false);
        botaoJogarRodada.setOnAction(event -> aoClicarJogarRodada());
        botaoEditarRodada.setVisible(false);

        scrollarSuavemente();
    }

    private void scrollarSuavemente() {
        containerDasRodadas.heightProperty().addListener((obs, oldVal, newVal) -> {
            Platform.runLater(() -> {
                Timeline timeline = new Timeline();
                KeyValue kv = new KeyValue(scrollPanePrincipal.vvalueProperty(), 1.0, Interpolator.EASE_BOTH);
                KeyFrame kf = new KeyFrame(Duration.millis(400), kv);
                timeline.getKeyFrames().add(kf);
                timeline.play();
            });
        });
    }

    private void adicionarVisualDaRodada(ObservableList<Partida> partidas) {
        String nomeRodada = gerarNomeDaRodada(partidas.size());
        Label tituloRodada = new Label(nomeRodada);
        tituloRodada.getStyleClass().add("rodada-title");

        TableView<Partida> tabelaDaRodada = criarTabelaParaRodada();
        tabelaDaRodada.setItems(partidas);
        ajustarAlturaDaTabela(tabelaDaRodada);

        containerDasRodadas.getChildren().addAll(tituloRodada, tabelaDaRodada);
    }

    private TableView<Partida> criarTabelaParaRodada() {
        TableView<Partida> tabela = new TableView<>();
        tabela.getStyleClass().add("results-table-view");

        TableColumn<Partida, Time> colunaTimeA = new TableColumn<>("Time A");
        colunaTimeA.setCellValueFactory(new PropertyValueFactory<>("time1"));

        TableColumn<Partida, Time> colunaTimeB = new TableColumn<>("Time B");
        colunaTimeB.setCellValueFactory(new PropertyValueFactory<>("time2"));

        TableColumn<Partida, Void> colunaVs = new TableColumn<>("");
        colunaVs.setMaxWidth(80);
        colunaVs.setMinWidth(80);
        colunaVs.setStyle("-fx-alignment: CENTER;");

        Callback<TableColumn<Partida, Time>, TableCell<Partida, Time>> fabricaDeCelulas = criarFabricaDeCelulasDeTime();
        colunaTimeA.setCellFactory(fabricaDeCelulas);
        colunaTimeB.setCellFactory(fabricaDeCelulas);
        colunaVs.setCellFactory(criarFabricaDeCelulasVs());

        tabela.getColumns().addAll(colunaTimeA, colunaVs, colunaTimeB);
        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        return tabela;
    }

    @FXML
    void aoClicarJogarRodada() {
        this.isEditing = false;
        abrirJanelaVencedores(historicoDeRodadas.get(historicoDeRodadas.size() - 1));
    }

    @FXML
    void aoClicarEditarRodada() {
        this.isEditing = true;
        abrirJanelaVencedores(historicoDeRodadas.get(historicoDeRodadas.size() - 1));
    }

    public void rodadaConcluida() {
        this.isEditing = false;
        atualizarVisualDaTabelaAtiva();
        botaoEditarRodada.setVisible(true);

        Tabela tabelaSimulada = new Tabela(new ArrayList<>(tabela.getTimes()));
        tabelaSimulada.avancarParaProximaFase(historicoDeRodadas.get(historicoDeRodadas.size() - 1));

        if (tabelaSimulada.temCampeao()) {
            exibirCampeao();
        } else {
            botaoJogarRodada.setText("Próxima Rodada");
            botaoJogarRodada.setOnAction(event -> avancarParaProximaRodada());
        }
    }

    public void atualizarVisualDaTabelaAtiva() {
        if (!containerDasRodadas.getChildren().isEmpty()) {
            Node ultimoNo = containerDasRodadas.getChildren().get(containerDasRodadas.getChildren().size() - 1);
            if (ultimoNo instanceof TableView) {
                ((TableView<Partida>) ultimoNo).refresh();
            }
        }
    }

    public void habilitarBotaoDeEdicao() {
        this.botaoEditarRodada.setVisible(true);
    }

    private void desabilitarTabelaAnterior() {
        if (containerDasRodadas.getChildren().size() >= 2) {
            Node tabelaAnterior = containerDasRodadas.getChildren().get(containerDasRodadas.getChildren().size() - 1);
            Node tituloAnterior = containerDasRodadas.getChildren().get(containerDasRodadas.getChildren().size() - 2);
            tabelaAnterior.setDisable(true);
            tituloAnterior.setDisable(true);
        }
    }

    private void exibirCampeao() {
        atualizarVisualDaTabelaAtiva();
        desabilitarTabelaAnterior();

        botaoJogarRodada.setText("Exibir Estatísticas");
        botaoJogarRodada.setDisable(true);
        botaoEditarRodada.setVisible(false);
    }

    private void abrirJanelaVencedores(ObservableList<Partida> partidas) {
        if (partidas == null || partidas.isEmpty()) return;
        try {
            URL fxmlLocal = HelloApplication.class.getResource("/tech/w3ll/egameleague/torneio/selecionar-vencedores-view.fxml");
            FXMLLoader carregador = new FXMLLoader(fxmlLocal);
            Parent raiz = carregador.load();

            SelecionarVencedoresController controlador = carregador.getController();
            controlador.carregarPartidas(partidas, this, this.isEditing);

            Stage janela = new Stage();
            janela.setTitle("Definir Vencedores da Rodada");
            janela.initModality(Modality.APPLICATION_MODAL);

            double alturaBase = 200.0;
            double alturaPorPartida = 100.0;
            double alturaCalculada = alturaBase + (partidas.size() * alturaPorPartida);
            double alturaMaxima = 700.0;

            double alturaFinal = Math.min(alturaCalculada, alturaMaxima);

            janela.setScene(new Scene(raiz, 450, alturaFinal));

            janela.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String gerarNomeDaRodada(int numeroDePartidas) {
        if (numeroDePartidas == 1) return "Final";
        if (numeroDePartidas == 2) return "Semifinais";
        if (numeroDePartidas <= 4) return "Quartas de Final";
        if (numeroDePartidas <= 8) return "Oitavas de Final";
        if (numeroDePartidas <= 16) return "16-avos de Final";
        return "Rodada " + this.rodadaAtualNumero;
    }

    private Callback<TableColumn<Partida, Time>, TableCell<Partida, Time>> criarFabricaDeCelulasDeTime() {
        return coluna -> new TableCell<>() {
            @Override
            protected void updateItem(Time item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                    getStyleClass().removeAll("winner-cell", "loser-cell");
                } else {
                    setText(item.getNome());
                    Partida partida = getTableRow().getItem();
                    getStyleClass().removeAll("winner-cell", "loser-cell");
                    if (partida != null && partida.getVencedor() != null) {
                        if (partida.getVencedor().equals(item)) {
                            getStyleClass().add("winner-cell");
                        } else {
                            getStyleClass().add("loser-cell");
                        }
                    }
                }
            }
        };
    }

    private Callback<TableColumn<Partida, Void>, TableCell<Partida, Void>> criarFabricaDeCelulasVs() {
        return coluna -> new TableCell<>() {
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setText(null);
                } else {
                    setText("x");
                }
            }
        };
    }

    private void ajustarAlturaDaTabela(TableView<Partida> tabela) {
        tabela.setFixedCellSize(ALTURA_DA_LINHA);
        Region cabecalho = (Region) tabela.lookup(".column-header-background");
        double alturaCabecalho = 45.0;
        if (cabecalho != null && cabecalho.isVisible()) {
            alturaCabecalho = cabecalho.getHeight();
        }
        int numeroDeLinhas = tabela.getItems().size();
        double alturaTotal = alturaCabecalho + (numeroDeLinhas * ALTURA_DA_LINHA) + 2;
        tabela.setPrefHeight(alturaTotal);
    }
}
