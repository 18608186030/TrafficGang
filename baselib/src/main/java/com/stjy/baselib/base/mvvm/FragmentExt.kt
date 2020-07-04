import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import com.stjy.baselib.base.BaseApplication
import com.stjy360.basicres.ViewModelFactory

fun <T : ViewModel> Fragment.obtainViewModel(viewModelClass: Class<T>) =
        ViewModelProviders.of(this, ViewModelFactory.newInstance(BaseApplication.application)).get(viewModelClass)

fun Fragment.getApplication() = BaseApplication.application