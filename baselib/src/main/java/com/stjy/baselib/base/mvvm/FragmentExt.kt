import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import com.stjy.baselib.base.MyAppliction
import com.stjy360.basicres.ViewModelFactory

fun <T : ViewModel> Fragment.obtainViewModel(viewModelClass: Class<T>) =
        ViewModelProviders.of(this, ViewModelFactory.newInstance(MyAppliction.application)).get(viewModelClass)

fun Fragment.getApplication() = MyAppliction.application