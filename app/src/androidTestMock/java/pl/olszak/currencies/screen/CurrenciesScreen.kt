package pl.olszak.currencies.screen

import android.view.View
import com.agoda.kakao.common.views.KView
import com.agoda.kakao.edit.KEditText
import com.agoda.kakao.image.KImageView
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.text.KTextView
import com.kaspersky.kaspresso.screens.KScreen
import org.hamcrest.Matcher
import pl.olszak.currencies.R
import pl.olszak.currencies.view.CurrenciesFragment

object CurrenciesScreen : KScreen<CurrenciesScreen>() {

    override val layoutId: Int? = R.layout.fragment_currencies
    override val viewClass: Class<*>? = CurrenciesFragment::class.java

    val itemContainer = KRecyclerView(
        builder = {
            withId(R.id.item_container)
        },
        itemTypeBuilder = {
            itemType(::CurrencyRow)
        }
    )
    val connectionErrorText = KTextView {
        withId(R.id.connection_error)
    }

    val errorContainer = KView {
        withId(R.id.error_container)
    }

    class CurrencyRow(parent: Matcher<View>) : KRecyclerItem<CurrencyRow>(parent) {
        val flagImage = KImageView(parent) {
            withId(R.id.flag_image)
        }
        val currencyCodeText = KTextView(parent) {
            withId(R.id.currency_code_text)
        }
        val currencyNameText = KTextView(parent) {
            withId(R.id.currency_name_text)
        }
        val currencyEdit = KEditText(parent) {
            withId(R.id.currency_edit)
        }
    }
}
