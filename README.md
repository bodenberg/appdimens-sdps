# AppDimens SDP, HDP, WDP

![AppDimens Banner](IMAGES/banner_top.png)

Bem-vindo à documentação oficial da biblioteca **AppDimens**. 

## 📖 O que é a biblioteca?

A **AppDimens SDP, HDP, WDP** é um sistema moderno de gerenciamento de dimensões para Android. Ela expande o padrão clássico do SDP (Scaled Density Pixels) introduzindo também o dimensionamento por Altura (HDP) e Largura (WDP). A biblioteca automatiza o processo de ajuste de Dp, Sp e Px, assegurando que o layout permaneça perfeitamente escalado e responsivo em qualquer formato de dispositivo de maneira matematicamente precisa.

## ⚙️ O que ela faz?

Ela fornece milhares de recursos `@dimen` pré-calculados (de `-300` a `600`) prontos para usar, poupando ao desenvolvedor o trabalho de calcular tamanhos para cada variante de tela de Android.

* **SDP (Smallest Width DP):** Escala a dimensão com base na menor largura (Smallest Width) disponível do dispositivo. Perfeito para manter a proporção da tela na maioria dos cenários (ex: `@dimen/_16sdp` ou `16.sdp`).
* **WDP (Width DP):** Escala especificamente fundamentada na largura horizontal exata do dispositivo na orientação atual (ex: `@dimen/_16wdp` ou `16.wdp`).
* **HDP (Height DP):** Escala especificamente fundamentada na altura vertical exata do dispositivo (ex: `@dimen/_16hdp` ou `16.hdp`).
* **Condicionais Dinâmicas (Compose):** Facilita a adaptação da dimensão baseada no tipo de dispositivo (Carro, TV, Relógio) através da instrução `.scaledDp()`.

![Exemplo do layout](IMAGES/screenshot.png)

### 📏 Unidades Físicas (DimenPhysicalUnits)

Além do dimensionamento relativo de tela, a biblioteca provê suporte direto para a conversão de unidades de medidas físicas reais (Milímetros, Centímetros e Polegadas) para uso no layout, garantindo tamanho absoluto independentemente da densidade do aparelho.

* **Extensões nativas no Compose:** Utilize `.mm`, `.cm` e `.inch` diretamente em `Float` ou `Int` para obter o valor equivalente em `Dp` ou `Px`.
  * *Exemplo:* `10.mm`, `2.5f.cm`, `1.inch`.
* **Utilitários de Raio e Diâmetro:** Funções convenientes como `.radius()` facilitam o desenho de componentes circulares com medidas físicas.
* **Uso no código legado (XML/Java):** A classe utilitária `DimenPhysicalUnits` apresenta métodos acessíveis (ex: `toDpFromMm`, `toPxFromCm`) que necessitam do `Resources` atual para realizar conversões vitais baseadas no DPI do dispositivo (padrão Android).

## 🚀 Vantagens

1. **Desenvolvimento Acelerado:** Elimina a necessidade de criar arquivos `dimens.xml` manuais massivos para várias categorias de telas (como `values-sw320dp`, `values-sw600dp`, etc.). Tudo já vem unificado.
2. **Integração Híbrida Direta:** Funciona incrivelmente bem tanto no tradicional **XML** (`View System`) quanto na era moderna do **Jetpack Compose**.
3. **Layouts Mais Limpos:** Com a arquitetura fluente do Compose (ex: `16.sdp`), as margens ficam legíveis e concisas.
4. **Precisão para TV, Wear OS e Auto:** Trata regras avançadas sem complexidade usando `UiModeType` combinados a qualificadores.

## ⚡ Performance

A implementação garante um impacto zero ou virtualmente nulo na performance:
* **No XML:** Todas as tags como `@dimen/_16sdp` são processadas estaticamente no build time e resolvidas de forma nativa e paralela aos recursos do Android Framework.
* **No Compose:** O acesso a `.sdp`, `.hdp` e `.wdp` usa funções otimizadas que extraem as dimensões via context caching nativo (`LocalConfiguration` e IDs injetados). Evitando o processamento desnecessário, ela respeita as etapas convencionais da UI sem forçar recomposições inúteis.

## 🛠️ Suporte e Instalação

A biblioteca tem amplo suporte no ecossistema Android e é atualizada para os paradigmas mais recém-lançados.

* **Min SDK:** 24
* **Compile SDK:** 36
* **Linguagens:** Kotlin e Java.
* **Paradigma:** XML e Jetpack Compose.

Para instalar, basta adicionar no seu `build.gradle` (dependência):

```kotlin
dependencies {
    implementation("io.github.bodenberg:appdimens-sdps:3.0.0")
}
```

### Exemplo Rápido no Compose:
```kotlin
Box(
    modifier = Modifier
        .width(100.wdp) // Escala conforme a largura
        .height(100.hdp) // Escala conforme a altura
        .padding(16.sdp) // Escala baseada no Smallest Width
)
```

### Exemplo Condicional Avançado:
```kotlin
val dynamicPadding = 16.dp.scaledDp()
    .screen(UiModeType.TELEVISION, customValue = 32.sdp)
    .sdp // Resultado: 32.sdp on TV, 16.sdp nos demais
```

![Demonstração extra](IMAGES/image.png)

---
*Criado com as melhores práticas de layout responsivo para o ecossistema Android.*
