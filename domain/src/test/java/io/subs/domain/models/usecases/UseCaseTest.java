package io.subs.domain.models.usecases;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.TestScheduler;
import io.subs.domain.executor.IPostExecutionThread;
import io.subs.domain.executor.IThreadExecutor;
import io.subs.domain.usecases.UseCase;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class) public class UseCaseTest {

    @Rule public final ExpectedException expectedException = ExpectedException.none();
    private UseCaseTestClass useCase;
    private TestDisposableObserver<Object> testObserver;
    @Mock private IThreadExecutor mockThreadExecutor;
    @Mock private IPostExecutionThread mockPostExecutionThread;

    @Before public void setUp() {
        this.useCase = new UseCaseTestClass(mockThreadExecutor, mockPostExecutionThread);
        this.testObserver = new TestDisposableObserver<>();
        given(mockPostExecutionThread.getScheduler()).willReturn(new TestScheduler());
    }

    @Test public void testBuildUseCaseObservableReturnCorrectResult() {
        useCase.execute(testObserver, Params.EMPTY);
        assertThat(testObserver.valuesCount).isZero();
    }

    @Test public void testSubscriptionWhenExecutingUseCase() {
        useCase.execute(testObserver, Params.EMPTY).dispose();
        assertThat(testObserver.isDisposed()).isTrue();
    }

    @Test public void testShouldFailWhenExecuteWithNullObserver() {
        expectedException.expect(NullPointerException.class);
        useCase.execute(null, Params.EMPTY);
    }

    private static class UseCaseTestClass extends UseCase<Object, Params> {

        UseCaseTestClass(IThreadExecutor threadExecutor, IPostExecutionThread postExecutionThread) {
            super(threadExecutor, postExecutionThread);
        }

        @Override public Observable<Object> buildUseCaseObservable(Params params) {
            return Observable.empty();
        }
    }

    private static class TestDisposableObserver<T> extends DisposableObserver<T> {
        private int valuesCount = 0;

        @Override public void onNext(T value) {
            valuesCount++;
        }

        @Override public void onError(Throwable e) {
            // no-op by default.
        }

        @Override public void onComplete() {
            // no-op by default.
        }
    }

    private static class Params {
        private static final Params EMPTY = new Params();

        private Params() {
        }
    }
}
