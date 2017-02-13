package com.tc.ui.renewed.itemcontainer;

import com.tc.ui.renewed.ui.ValueableContainer;

import totalcross.ui.Container;
import totalcross.ui.Edit;
import totalcross.ui.Label;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.FocusListener;

public class RestauranteBuilderView extends ValueableContainer<Restaurante> {
	private static final String intCharset = Edit.numbersSet;
	private static final String numericCharset = intCharset + ".";
	
	Label lblNome;
	Label lblSatisfacao;
	Label lblTipo;
	Label lblPreco;
	Label lblDist;
	Label lblTempo;
	
	Edit edtNome;
	Edit edtSatisfacao;
	Edit edtTipo;
	Edit edtPreco;
	Edit edtDist;
	Edit edtTempo;
	
	public RestauranteBuilderView() {
		super(new RestauranteBuilder());
		
		lblNome = new Label("Nome: ");
		lblSatisfacao = new Label("Satisfacao: ");
		lblTipo = new Label("Tipo: ");
		lblPreco = new Label("Preco: ");
		lblDist = new Label("Distancia: ");
		lblTempo = new Label("Tempo: ");
		
		edtNome = new Edit();
		edtSatisfacao = new Edit();
		edtTipo = new Edit();
		edtPreco = new Edit();
		edtDist = new Edit();
		edtTempo = new Edit();
		
		edtSatisfacao.setValidChars(numericCharset);
		edtPreco.setValidChars(intCharset);
		edtDist.setValidChars(numericCharset);
		edtTempo.setValidChars(intCharset);
		
		reset();
	}
	
	public void reset() {
		getBuilder().reset();
		loadBuilder();
	}
	
	public void load(Restaurante restaurante) {
		getBuilder().getInfo(restaurante);
		_load(restaurante);
	}

	private void loadBuilder() {
		_load(getBuilder());
	}
	
	private void _load(Restaurante restaurante) {
		edtNome.setText(restaurante.getNome());
		edtSatisfacao.setText(restaurante.getSatisfacao());
		edtTipo.setText(restaurante.getTipo());
		edtPreco.setText(restaurante.getPreco());
		edtDist.setText(restaurante.getDist());
		edtTempo.setText(restaurante.getTempo());
	}

	@Override
	public void initUI() {
		FocusListener focusListener = new FocusListener() {
			
			@Override
			public void focusOut(ControlEvent e) {
				Edit edt = (Edit) e.target;
				
				doAction(edt);
			}

			@Override
			public void focusIn(ControlEvent e) {
			}
			
			private void doAction(Edit edt) {
				String text = edt.getText();
				if (edt == edtNome) {
					getBuilder().setNome(text);
				} else if (edt == edtSatisfacao) {
					getBuilder().setSatisfacao(text);
				} else if (edt == edtTipo) {
					getBuilder().setTipo(text);
				} else if (edt == edtPreco) {
					getBuilder().setPreco(text);
				} else if (edt == edtDist) {
					getBuilder().setDist(text);
				} else if (edt == edtTempo) {
					getBuilder().setTempo(text);
				}
			}
		};
		
		edtNome.addFocusListener(focusListener);
		edtSatisfacao.addFocusListener(focusListener);
		edtTipo.addFocusListener(focusListener);
		edtPreco.addFocusListener(focusListener);
		edtDist.addFocusListener(focusListener);
		edtTempo.addFocusListener(focusListener);
		
		Container divLikeLabelContainer = new Container();
		add(divLikeLabelContainer, LEFT, TOP, WILL_RESIZE, FILL);
		divLikeLabelContainer.add(lblNome, LEFT, TOP, PREFERRED, PREFERRED);
		divLikeLabelContainer.add(lblSatisfacao, LEFT, AFTER, PREFERRED, PREFERRED);
		divLikeLabelContainer.add(lblTipo, LEFT, AFTER, PREFERRED, PREFERRED);
		divLikeLabelContainer.add(lblPreco, LEFT, AFTER, PREFERRED, PREFERRED);
		divLikeLabelContainer.add(lblDist, LEFT, AFTER, PREFERRED, PREFERRED);
		divLikeLabelContainer.add(lblTempo, LEFT, AFTER, PREFERRED, PREFERRED);
		divLikeLabelContainer.resizeWidth();
		
		Container divLikeEdtContainer = new Container();
		add(divLikeEdtContainer, AFTER, SAME, FILL, FILL);
		divLikeEdtContainer.add(edtNome, LEFT, TOP, FILL, lblNome.getHeight());
		divLikeEdtContainer.add(edtSatisfacao, LEFT, AFTER, FILL, SAME);
		divLikeEdtContainer.add(edtTipo, LEFT, AFTER, FILL, SAME);
		divLikeEdtContainer.add(edtPreco, LEFT, AFTER, FILL, SAME);
		divLikeEdtContainer.add(edtDist, LEFT, AFTER, FILL, SAME);
		divLikeEdtContainer.add(edtTempo, LEFT, AFTER, FILL, SAME);
	}
	
	private RestauranteBuilder getBuilder() {
		return (RestauranteBuilder) super.getValue();
	}
	
	@Override
	public int getPreferredHeight() {
		return lblNome.getPreferredHeight() + lblSatisfacao.getPreferredHeight() +
				lblTipo.getPreferredHeight() + lblPreco.getPreferredHeight() +
				lblDist.getPreferredHeight() + lblTempo.getPreferredHeight();
	}
	
	@Override
	public Restaurante getValue() {
		return getBuilder().build();
	}

}
