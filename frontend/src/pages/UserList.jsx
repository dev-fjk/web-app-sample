import { useState, useEffect } from "react";
import { Link } from "react-router-dom";

function UserList() {
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [searchParams, setSearchParams] = useState({
    userName: "",
    email: "",
    phoneNumber: "",
  });
  const [pagination, setPagination] = useState({
    page: 1,
    pageSize: 10,
    totalCount: 0,
    totalPages: 0,
  });

  useEffect(() => {
    fetchUsers();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [pagination.page]);

  const fetchUsers = async () => {
    try {
      setLoading(true);
      const params = new URLSearchParams({
        page: pagination.page.toString(),
        pageSize: pagination.pageSize.toString(),
      });

      if (searchParams.userName) {
        params.append("userName", searchParams.userName);
      }
      if (searchParams.email) {
        params.append("email", searchParams.email);
      }
      if (searchParams.phoneNumber) {
        params.append("phoneNumber", searchParams.phoneNumber);
      }

      const response = await fetch(`/api/v1/users?${params.toString()}`);
      if (!response.ok) {
        throw new Error("ユーザーリストの取得に失敗しました");
      }
      const data = await response.json();
      setUsers(data.data || []);
      setPagination((prev) => ({
        ...prev,
        totalCount: data.pagination?.totalCount || 0,
        totalPages: data.pagination?.totalPages || 0,
      }));
      setError(null);
    } catch (err) {
      setError(err.message);
      setUsers([]);
    } finally {
      setLoading(false);
    }
  };

  const handlePageChange = (newPage) => {
    setPagination((prev) => ({ ...prev, page: newPage }));
  };

  const handleSearchChange = (field, value) => {
    setSearchParams((prev) => ({ ...prev, [field]: value }));
  };

  const handleSearchSubmit = (e) => {
    e.preventDefault();
    setPagination((prev) => ({ ...prev, page: 1 }));
    fetchUsers();
  };

  const handleClearSearch = () => {
    setSearchParams({
      userName: "",
      email: "",
      phoneNumber: "",
    });
    setPagination((prev) => ({ ...prev, page: 1 }));
    fetchUsers();
  };

  if (loading) {
    return (
      <div className="flex justify-center items-center h-64">
        <div className="text-gray-600">読み込み中...</div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded">
        {error}
      </div>
    );
  }

  return (
    <div>
      <h1 className="text-3xl font-bold text-gray-900 mb-6">ユーザーリスト</h1>

      {/* 検索ボックス */}
      <div className="bg-white rounded-lg shadow p-6 mb-6">
        <form onSubmit={handleSearchSubmit} className="space-y-4">
          <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
            <div>
              <label
                htmlFor="userName"
                className="block text-sm font-medium text-gray-700 mb-1"
              >
                ユーザー名
              </label>
              <input
                type="text"
                id="userName"
                value={searchParams.userName}
                onChange={(e) => handleSearchChange("userName", e.target.value)}
                className="w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
                placeholder="ユーザー名で検索"
              />
            </div>
            <div>
              <label
                htmlFor="email"
                className="block text-sm font-medium text-gray-700 mb-1"
              >
                メールアドレス
              </label>
              <input
                type="text"
                id="email"
                value={searchParams.email}
                onChange={(e) => handleSearchChange("email", e.target.value)}
                className="w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
                placeholder="メールアドレスで検索"
              />
            </div>
            <div>
              <label
                htmlFor="phoneNumber"
                className="block text-sm font-medium text-gray-700 mb-1"
              >
                電話番号
              </label>
              <input
                type="text"
                id="phoneNumber"
                value={searchParams.phoneNumber}
                onChange={(e) =>
                  handleSearchChange("phoneNumber", e.target.value)
                }
                className="w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
                placeholder="電話番号で検索"
              />
            </div>
          </div>
          <div className="flex space-x-2">
            <button
              type="submit"
              className="px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2"
            >
              検索
            </button>
            <button
              type="button"
              onClick={handleClearSearch}
              className="px-4 py-2 bg-gray-200 text-gray-700 rounded-md hover:bg-gray-300 focus:outline-none focus:ring-2 focus:ring-gray-500 focus:ring-offset-2"
            >
              クリア
            </button>
          </div>
        </form>
      </div>

      {users.length === 0 ? (
        <div className="bg-white rounded-lg shadow p-6 text-center text-gray-600">
          ユーザーが見つかりませんでした
        </div>
      ) : (
        <>
          <div className="bg-white rounded-lg shadow overflow-hidden">
            <table className="min-w-full divide-y divide-gray-200">
              <thead className="bg-gray-50">
                <tr>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    ID
                  </th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    ユーザー名
                  </th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    メールアドレス
                  </th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    電話番号
                  </th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    操作
                  </th>
                </tr>
              </thead>
              <tbody className="bg-white divide-y divide-gray-200">
                {users.map((user) => (
                  <tr key={user.id} className="hover:bg-gray-50">
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                      {user.id}
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                      {user.userName}
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                      {user.email}
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                      {user.phoneNumber || "-"}
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm font-medium">
                      <Link
                        to={`/users/${user.id}`}
                        className="text-blue-600 hover:text-blue-900"
                      >
                        詳細
                      </Link>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>

          {pagination.totalPages > 1 && (
            <div className="mt-6 flex justify-center items-center space-x-2">
              <button
                onClick={() => handlePageChange(pagination.page - 1)}
                disabled={pagination.page === 1}
                className="px-4 py-2 border border-gray-300 rounded-md text-sm font-medium text-gray-700 bg-white hover:bg-gray-50 disabled:opacity-50 disabled:cursor-not-allowed"
              >
                前へ
              </button>
              <span className="text-sm text-gray-700">
                {pagination.page} / {pagination.totalPages} ページ （全{" "}
                {pagination.totalCount} 件）
              </span>
              <button
                onClick={() => handlePageChange(pagination.page + 1)}
                disabled={pagination.page >= pagination.totalPages}
                className="px-4 py-2 border border-gray-300 rounded-md text-sm font-medium text-gray-700 bg-white hover:bg-gray-50 disabled:opacity-50 disabled:cursor-not-allowed"
              >
                次へ
              </button>
            </div>
          )}
        </>
      )}
    </div>
  );
}

export default UserList;
