package br.com.jeffque.tc.multislider;

import br.com.jeffque.tc.ui.BaseContainer;
import br.com.jeffque.tc.ui.BaseTCMain;
import br.com.jeffque.tc.ui.ProportionalMultiSlider;
import br.com.jeffque.tc.ui.SliderValue;
import br.com.jeffque.tc.ui.builder.ProportionalMultiSliderBuilder;
import br.com.jeffque.tc.util.Presenter;
import br.com.jeffque.tc.util.ReadWriteAccessor;
import totalcross.sys.InvalidNumberException;
import totalcross.ui.Button;
import totalcross.ui.Edit;
import totalcross.ui.Label;
import totalcross.ui.ScrollContainer;
import totalcross.ui.Slider;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.PressListener;
import totalcross.util.BigDecimal;

public class MultiSliderTest extends BaseContainer {
	Edit valorSlider;
	Edit qtSlider;
	ScrollContainer multiSliderContainer = new ScrollContainer(false, true);
	
	public MultiSliderTest(BaseTCMain<?> baseTest) {
		super(baseTest);
		valorSlider = new Edit("1234567890");
		qtSlider = new Edit("1234567890");
	}
	
	BigDecimal getValue() {
		if (valorSlider.getText().length() != 0) {
			try {
				return new BigDecimal(valorSlider.getText());
			} catch (InvalidNumberException e) {
				e.printStackTrace();
			}
		}
		return BigDecimal.ONE;
	}
	
	int getQt() {
		if (qtSlider.getText().length() != 0) {
			return Integer.valueOf(qtSlider.getText());
		}
		return 2;
	}

	@Override
	public void initUI() {
		super.initUI();
		Label lbSliderTotal = new Label("Valor soma slider: ");
		valorSlider.setText("100");
		
		Label lbQtSlider = new Label("Valor Qt slider: ");
		qtSlider.setText("4");
		
		add(lbSliderTotal, LEFT, AFTER, PREFERRED, PREFERRED);
		add(valorSlider, AFTER, SAME, FILL, SAME, lbSliderTotal);
		
		add(lbQtSlider, LEFT, AFTER, PREFERRED, PREFERRED);
		add(qtSlider, AFTER, SAME, FILL, SAME, lbQtSlider);
		
		Button btn = new Button("Create Multi Slider");
		add(btn, LEFT, AFTER, FILL, PREFERRED);
		add(multiSliderContainer, LEFT, AFTER, FILL, FILL);
		
		btn.addPressListener(new PressListener() {
			
			@Override
			public void controlPressed(ControlEvent arg0) {
				multiSliderContainer.removeAll();
				
				ProportionalMultiSlider<BigDecimalWrapper> multiSlider = createMultiSlider();
				boolean firstTime = true;
				
				for (SliderValue<BigDecimalWrapper> s: multiSlider.getSliders()) {
					Slider tcs = s.getSlider();
					if (firstTime) {
						firstTime = false;
						multiSliderContainer.add(tcs, LEFT, TOP, FILL, PREFERRED);
					} else {
						multiSliderContainer.add(tcs, LEFT, AFTER, FILL, PREFERRED);
					}
				}
			}
		});
	}
	
	ProportionalMultiSlider<BigDecimalWrapper> createMultiSlider() {
		ProportionalMultiSliderBuilder<BigDecimalWrapper> builder = new ProportionalMultiSliderBuilder<>(new Presenter<BigDecimalWrapper>() {

			@Override
			public String stringfy(BigDecimalWrapper value) {
				return value.getValue() != null? value.getValue().toPlainString(): "";
			}
		}, new ReadWriteAccessor<BigDecimalWrapper, BigDecimal>() {

			@Override
			public BigDecimal getAttr(BigDecimalWrapper source) {
				return source.getValue();
			}

			@Override
			public void setAttr(BigDecimalWrapper source, BigDecimal value) {
				source.setValue(value);
			}
		});
		for (int i = getQt() - 1; i >= 0; i--) {
			builder.addValue(new BigDecimalWrapper());
		}
		builder.setSum(getValue());
		
		return builder.build();
	}
}
